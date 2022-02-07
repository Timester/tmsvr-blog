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
        scale: [0, 5000000, 10000000, 25000000, 50000000, 75000000, 100000000, 200000000, 1000000000, 8000000000]
    },
    {
        name: "GDP",
        key: "gdp_md_est",
        description: "The GDP of the country",
        unit: "USD",
        scale: [0, 10000, 50000, 100000, 500000, 1000000, 5000000, 1000000000]
    }
];

// from small to big, 15 colors https://colordesigner.io/gradient-generator
const colors = [
    '#fffddd',
    '#faf3c8',
    '#f6e8b3',
    '#f4dd9f',
    '#f3d18b',
    '#f2c578',
    '#f2b866',
    '#f2ab55',
    '#f39d46',
    '#f38e38',
    '#f47d2c',
    '#f56b23',
    '#f6571d',
    '#f63c1a',
    '#f6081b'
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

    const getColor = (val) => {
        for (let i = 1; i < dataScope.scale.length; i++) {
            if (val < dataScope.scale[i]) {
                return colors[i - 1];
            }
        }

        return colors[colors.length - 1];
    }

    const style = (feature) => {
        let mapStyle = {
            fillColor: getColor(feature.properties[dataScope.key]),
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