import { logout } from '../store/actions/authAction'
class Settings {
    constructor() {
        this.url = "https://ruwebdiary.herokuapp.com"
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

    checkToken = (res) => {
        console.log(res)
        if (!res.status) {
            if (res.error.description === "USER_NOT_FOUND") {
                logout();
            }
        }
    }
}

export default Settings;