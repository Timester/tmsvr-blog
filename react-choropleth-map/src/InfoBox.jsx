import './infobox.css';

function numberWithCommas(x) {
  if (x != null) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  } else {
    return '';
  }
}

// TODO link is not clickable, infobox seems to be "transparent"
export function InfoBox({ data, scope }) {
  let infoBox;
  if (data != null) {
    infoBox = <div><h4>{data.name}</h4>
      <br></br>
      <b>{scope.name}:</b> {numberWithCommas(data[scope.key])} {scope.unit}
      <br></br>
      <b>Description:</b> {scope.description}</div>;
  } else {
    infoBox = <h4><i>select a country</i></h4>;
  }

  return (
    <div className="info leaflet-top leaflet-right">
      {infoBox}
    </div>
  )
}