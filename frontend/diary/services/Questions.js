import Settings from "./Settings";

class Auth extends Settings {
    constructor() {
        super();
    }

    getAllQuestions = async (token) => {
        const url = this.url + "/questions?page=0"

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

    getFeedQuestions = async (token, type, page) => {
        const url = this.url + `/questions/feed?type=${type}&page=${page}`

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

    voteQuestion = async (token, req) => {
        const url = this.url + "/votes"

        const requestReg = {
            ...this.postReq,
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-type": 'application/json; charset=UTF-8'
            },
            ...req
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

    createQuestion = async (token, req) => {
        const url = this.url + "/questions"

        const requestReg = {
            ...this.postReq,
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-type": 'application/json; charset=UTF-8'
            },
            ...req
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

export default new Auth();