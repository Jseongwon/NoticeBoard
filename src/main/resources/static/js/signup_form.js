(async () => {
    const UserCryptHelper = (await import("./user-crypt-helper.js")).UserCryptHelper;

    const matchColor = "#00ff00";
    const wrongColor ="#ff0000";

    const formElement = document.getElementById('signupForm');

    const passwordInputElement = document.getElementById('passwordInput');
    const passwordConfirmElement = document.getElementById('passwordConfirm');
    const confirmMessageElement = document.getElementById('confirmMessage');

    const passwordElement = document.querySelector('#password');
    const saltElement = document.querySelector('#salt');

    const dangerMessageElement = document.querySelector('.alert-danger');

    function passwordConfirm() {
        let isMatched = false;
        if(passwordInputElement.value == passwordConfirmElement.value) {
            confirmMessageElement.style.color = matchColor;
            confirmMessageElement.innerHTML ="비밀번호 일치";
            isMatched = true;
        }
        else {
            confirmMessageElement.style.color = wrongColor;
            confirmMessageElement.innerHTML ="비밀번호 불일치";
        }
        return isMatched;
    }

    function isIDDuplicated() {

    }

    function onSignupButtonClicked(event) {
        let password;
        let hashedPassword;
        let salt;
        let isMatched;

        // 1. 비밀번호를 읽는다.
        password = passwordInputElement.value;

        isMatched = passwordConfirm();
        if(!isMatched) {
            moveErrorPage();
            dangerMessageElement.innerText = "<div>비밀번호가 일치하지 않습니다.</div>";
            event.preventDefault();
            return;
        }

        // 1. salt를 구한다.
        salt = UserCryptHelper.createSalt();
        console.log(salt.toString());

        // 2. 구한 salt 값을 저장한다.
        saltElement.value = salt;

        // 3. 비밀번호를 암호화한다.
        hashedPassword = UserCryptHelper.createHashedPassword(password, salt);

        // 4. 해싱된 비밀번호 값을 저장한다.
        passwordElement.value = hashedPassword;

        // true를 반환하면 form에서의 이벤트 처리가 진행된다.
        // const formElement = document.getElementById('signupForm');
        formElement.submit();
    }

    function moveErrorPage() {
        let index = window.location.href.indexOf('?error');
        if(index < 0) {
            window.location.href += '?error';
        }
    }

    document.querySelector("#signupButton").addEventListener('click', onSignupButtonClicked);
})();