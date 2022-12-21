'use strict'

export class Utils {
    static async XMLHttpRequestMacro(url, data = {}) {
        let responseData = undefined;
        let ajax;
        let promise;

        ajax = new XMLHttpRequest();
        promise = new Promise((resolve, reject) => {
            let parameter = document.querySelector("meta[name='_csrf_parameter']").content;
            let header = document.querySelector("meta[name='_csrf_header']").content;
            let token = document.querySelector("meta[name='_csrf']").content;

            // using XMLHttpRequest directly to send a non-x-www-form-urlencoded request
            ajax.open("POST", url, true);
            ajax.onload = function () {
                if (this.status >= 200 && this.status < 300) {
                    resolve(ajax.response);
                } else {
                    reject({
                        status: this.status,
                        statusText: ajax.statusText
                    });
                }
            };
            ajax.onerror = function () {
                reject({
                    status: this.status,
                    statusText: ajax.statusText
                });
            };
            ajax.setRequestHeader("Content-Type", "application/json");
            if(token && header) {
                ajax.setRequestHeader(header, token);
            }
        });
        ajax.send(JSON.stringify(data));

        try {
            responseData = await promise;
            console.log(responseData);
        }
        catch (e) {
            console.log(e);
        }
        return responseData;
    }

    static async fetchMacro(url, data = {}) {
        let responseData = undefined;
        let promise;
        let response;
        let parameter = document.querySelector("meta[name='_csrf_parameter']").content;
        let header = document.querySelector("meta[name='_csrf_header']").content;
        let token = document.querySelector("meta[name='_csrf']").content;
        let headers = {};

        headers[header] = token;
        headers["Content-Type"] = "application/json";

        promise = fetch(url, {
            headers,
            method: "POST",
            body: JSON.stringify(data)
        })
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            })
        try {
            response = await promise;
            responseData = await response.json();
        }
        catch (e) {
            console.log(e);
        }
         return responseData;
    }
}