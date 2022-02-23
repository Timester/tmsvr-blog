import Control from "react-leaflet-custom-control";
import { getColor } from './util';
import './legend.css';

export function Legend({ scope, colors, hoveredCountry }) {
    let legend = [];

    for (let i = -1; i < scope.scale.length; i++) {
        let from;
        let to;

        if (i === -1) {
            from = null;
            to = scope.scale[0];
        } else {
            from = scope.scale[i];
            to = scope.scale[i + 1];
        }

        let hoveredValue = null;
        if (hoveredCountry) {
            if (hoveredCountry[scope.key]) {
                hoveredValue = hoveredCountry[scope.key];
            }
        }

        let left = from ? from : 'min';
        let right = to;

        if (hoveredValue != null && (hoveredValue > from && (!to || hoveredValue < to))) {
            legend.push(<tr className="legendRowHighlighted" key={from}>
                <td><i style={{ background: getColor(from + 1, colors, scope.scale) }}></i></td>
                <td className='leftCol'>{left}</td><td>&nbsp;-&nbsp;</td><td className='rightCol'>{right ? right : '∞'}</td>
            </tr>);
        } else {
            legend.push(<tr className="legendRow" key={from}>
                <td><i style={{ background: getColor(from + 1, colors, scope.scale) }}></i></td>
                <td className='leftCol'>{left}</td><td>&nbsp;-&nbsp;</td><td className='rightCol'>{right ? right : '∞'}</td>
            </tr>);
        }
    }

    return (
        <Control position="bottomright">
            <div className="info legend">
                <table>
                    <tbody>
                        {legend}
                    </tbody>
                </table>
                <span id="legend-title"> {scope.unit ? '[' + scope.unit + ']' : ''}</span>
            </div>
        </Control>
    )
}