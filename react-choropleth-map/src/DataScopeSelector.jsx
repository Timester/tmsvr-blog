export function DataScopeSelector({ options, value, changeHandler }) {
    return (
        <select value={value.key} onChange={changeHandler}>
            {options.map((e) => {
                return <option key={e.key} value={e.key}>{e.name}</option>
            })}
        </select>
    );
}