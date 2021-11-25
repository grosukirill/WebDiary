export function MAIN_SET(payload: {
    key: string
    value: any
}) {
    return <const>{
        type: 'main/set',
        payload
    }
}