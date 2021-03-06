import Settings from "./Settings";

class Auth extends Settings {
    constructor() {
        super();
    }

    getUserInfo = async (token: string) => {
        const url = this.url + "/users/token"

        const requestReg = {
            ...this.getReq,
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-type": 'application/json; charset=UTF-8'
            }
        }

        const request = await fetch(url, requestReg);
        const json = await request.json();

        this.checkToken(json)

        return json;
    }
}

export default new Auth();