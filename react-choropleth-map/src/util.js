export function getColor(val, colors, scale) {
    if (!val) {
        return '#ddd';
    }

    let colorsToUse = colors[scale.length - 7];

    for (let i = 0; i < scale.length; i++) {
        if (val < scale[i]) {
            return colorsToUse[i];
        }
    }

    return colorsToUse[colorsToUse.length - 1];
}