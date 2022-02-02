import React from 'react';
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

export default class ChoroplethMap extends React.Component {
    constructor(props) {
        super(props);

        this.defaultStyle = {
            weight: 2,
            opacity: 1,
            color: 'white',
            dashArray: '3',
            fillOpacity: 0.7
        }

        this.highlightedStyle = {
            weight: 3,
            color: '#666',
            dashArray: '',
            fillOpacity: 0.7
        }

        this.state = {
            dataScope: dataScopes[0],
            data: null
        }

        this.getColor = this.getColor.bind(this);
        this.style = this.style.bind(this);
        this.onEachFeature = this.onEachFeature.bind(this);
        this.highlightFeature = this.highlightFeature.bind(this);
        this.resetHighlight = this.resetHighlight.bind(this);
        this.handleDataScopeChange = this.handleDataScopeChange.bind(this);
    }

    handleDataScopeChange(event) {
        // TODO dataScope changes as it should, but the map coloring wont change, must update the style
        this.setState({ dataScope: dataScopes.find(element => element.key === event.target.value) })
    }

    highlightFeature(e) {
        var layer = e.target;
        layer.bringToFront();
        layer.setStyle(this.highlightedStyle);
    }

    resetHighlight(e) {
        var layer = e.target;
        layer.setStyle(this.defaultStyle);
    }

    onEachFeature(feature, layer) {
        layer.on({
            mouseover: this.highlightFeature,
            mouseout: this.resetHighlight,
            click: () => this.setState({ data: feature.properties })
        });
    }

    getColor(val) {
        for (let i = 1; i < this.state.dataScope.scale.length; i++) {
            if (val < this.state.dataScope.scale[i]) {
                return colors[i - 1];
            }
        }

        return colors[colors.length - 1];
    }

    style(feature) {
        return {
            fillColor: this.getColor(feature.properties[this.state.dataScope.key]),
            weight: 2,
            opacity: 1,
            color: 'white',
            dashArray: '3',
            fillOpacity: 0.7
        };
    }

    render() {
        return (
            <div className='mapContainer'>
                <MapContainer center={[51.505, -0.09]} zoom={3}>
                    <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
                    <GeoJSON data={countries} style={this.style} onEachFeature={this.onEachFeature} />
                    <InfoBox data={this.state.data} scope={this.state.dataScope} />
                </MapContainer>
                <DataScopeSelector options={dataScopes} value={this.state.dataScope} changeHandler={this.handleDataScopeChange} />
            </div>
        );
    }
}