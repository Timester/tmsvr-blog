import React, { useState } from 'react';
import { MapContainer, TileLayer, GeoJSON } from 'react-leaflet'
import { InfoBox } from './InfoBox';
import { DataScopeSelector } from './DataScopeSelector';

import './index.css';

import countries from './custom.geo.50.json';

const dataScopes = [
    {
        name: "Population",
        key: "pop_est",
        description: "The population of the country",
        unit: "",
        scale: [5000000, 10000000, 25000000, 50000000, 75000000, 100000000, 200000000, 1000000000]
    },
    {
        name: "GDP",
        key: "gdp_md_est",
        description: "The GDP of the country",
        unit: "USD",
        scale: [100000, 250000, 500000, 5000000, 15000000]
    }
];

const colors = [
    ['#ffffcc', '#c7e9b4', '#7fcdbb', '#41b6c4', '#2c7fb8', '#253494'],
    ['#ffffd9', '#edf8b1', '#c7e9b4', '#7fcdbb', '#41b6c4', '#1d91c0', '#225ea8', '#253494', '#081d58']
]

export default function ChoroplethMap() {
    const [dataScope, setDataScope] = useState(dataScopes[0]);
    const [selectedCountry, setSelectedCountry] = useState(null);
    const [hoveredCountry, setHoveredCountry] = useState(null);

    const handleDataScopeChange = (event) => {
        setDataScope(dataScopes.find(element => element.key === event.target.value));
    }

    const highlightFeature = (e) => {
        let layer = e.target;
        setHoveredCountry(layer.feature.properties);
    }

    const resetHighlight = (e) => {
        setHoveredCountry(null);
    }

    const onEachFeature = (feature, layer) => {
        layer.on({
            mouseover: highlightFeature,
            mouseout: resetHighlight,
            click: () => setSelectedCountry(feature.properties)
        });
    }

    const getColor = (val, scale) => {
        if (!val) {
            return '#ddd';
        }
    
        let colorsToUse = colors[scale.length == 5 ? 0 : 1];
    
        for (let i = 0; i < scale.length; i++) {
            if (val < scale[i]) {
                return colorsToUse[i];
            }
        }
    
        return colorsToUse[colorsToUse.length - 1];
    }

    const style = (feature) => {
        let mapStyle = {
            fillColor: getColor(feature.properties[dataScope.key], dataScope.scale),
            weight: 1,
            opacity: 1,
            color: '#888',
            dashArray: '3',
            fillOpacity: 0.7
        };

        if (hoveredCountry && feature.properties.iso_a3 === hoveredCountry.iso_a3) {
            mapStyle.color = '#444';
            mapStyle.weight = 2;
        }

        return mapStyle;
    }

    return (
        <div className='mapContainer' >
            <MapContainer center={[51.505, -0.09]} zoom={3}>
                <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
                <GeoJSON data={countries} style={style} onEachFeature={onEachFeature} />
                <InfoBox data={selectedCountry} scope={dataScope} />
            </MapContainer>
            <DataScopeSelector options={dataScopes} value={dataScope} changeHandler={handleDataScopeChange} />
        </div>
    );
}