import Settings from "./Settings";

class Static extends Settings {
    constructor() {
        super();
    }

    getCategories = async (token: string) => {
        const url = this.url + "/questions/categories"

        const requestReg = {
            ...this.getReq,
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-type": 'application/json; charset=UTF-8'
            }
        }

        try {
            const request = await fetch(url, requestReg);
            const json = await request.json();

            this.checkToken(json)

            return json;
        }
        catch (e) {
            return {
                status: false
            }
        }
    }
}

export default new Static();