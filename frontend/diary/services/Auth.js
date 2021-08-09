import Settings from "./Settings";

class Auth extends Settings {
    constructor() {
        super();
    }

    startRegistration = async (req) => {
        const url = this.url + "/users"

        const requestReg = {
            ...this.postReq,
            ...req
        }

        const request = await fetch(url, requestReg);
        const json = await request.json();

        return json;
    }

    startAuthentication = async (req) => {
        const url = this.url + "/auth"

        const requestReg = {
            ...this.postReq,
            ...req
        }

        const request = await fetch(url, requestReg);
        const json = await request.json();

        return json;
    }
}

export default new Auth();