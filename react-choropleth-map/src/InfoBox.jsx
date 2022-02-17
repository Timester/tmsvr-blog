import Control from "react-leaflet-custom-control";

import './infobox.css';

function numberWithCommas(x) {
  if (x != null) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  } else {
    return '';
  }
}

export function InfoBox({ data, scope }) {
  let infoBox;
  if (data != null) {
    infoBox = <div className="info"><h4>{data.name}</h4>
      <br></br>
      <b>{scope.name}:</b> {numberWithCommas(data[scope.key])} {scope.unit}
      <br></br>
      <b>Description:</b> {scope.description}</div>;
  } else {
    infoBox = <h4><i>select a country</i></h4>;
  }

  return (
    <Control position='topright'>
      {infoBox}
    </Control>
  )
}