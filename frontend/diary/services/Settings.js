class Settings {
    constructor() {
        this.url = "http://localhost:8080"
        this.postReq = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            }
        }
        this.getReq = {
            method: "GET"
        }
        this.deleteReq = {
            method: "DELETE"
        }
        this.putReq = {
            method: "PUT"
        }
        this.lang = "ro"
    }
}

export default Settings;