(async () => {
    const UserCryptHelper = (await import("./user-crypt-helper.js")).UserCryptHelper;
    const Utils = (await import("./utils.js")).Utils;

    const formElement = document.getElementById('loginForm');
    const loginButtonElement = document.getElementById('loginButton');

    const nameElement = document.getElementById('name');
    const passwordInputElement = document.getElementById('passwordInput');

    const passwordElement = document.querySelector("#password");

    const dangerMessageElement = document.querySelector('.alert-danger');

    async function onLoginButtonClicked(event) {
        let responseData;
        let name;
        let password;
        let hashedPassword;
        let salt;

        // 1. 사용자 ID를 읽는다.
        name = nameElement.value;
        if(!name) {
            moveErrorPage();
            return;
        }

        // 2. 비밀번호를 읽는다.
        password = passwordInputElement.value;
        if(!password) {
            moveErrorPage();
            return;
        }

        // 2. salt를 요청한다.
        responseData = await Utils.XMLHttpRequestMacro('/user/login/salt', {name: name});
        salt = JSON.parse(responseData).salt;

        // 4. 비밀번호를 암호화한다.
        console.log(password);
        console.log(salt);
        hashedPassword = UserCryptHelper.createHashedPassword(password, salt);

        // 5. 해싱된 비밀번호 값을 저장한다.
        passwordElement.value = hashedPassword;

        formElement.submit();
    }

    function moveErrorPage() {
        let index = window.location.href.indexOf('?error');
        if(index < 0) {
            window.location.href += '?error';
        }
    }

    loginButtonElement.addEventListener('click', onLoginButtonClicked);
})();