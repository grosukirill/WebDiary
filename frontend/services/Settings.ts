import { logout } from '../store/actions/authActions'

interface IMethod {
    method: string
}

class Settings {
    url: string
    postReq: {
        method: string
        headers: {
            'Content-Type': string
        }
    }
    getReq: IMethod
    deleteReq: IMethod
    putReq: IMethod
    lang: string

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

    checkToken = (res: any) => {
        if (!res.status) {
            if (res.error.description === "USER_NOT_FOUND") {
                logout();
            }
        }
    }
}

export default Settings;