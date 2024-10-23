import {initializeApp} from "https://www.gstatic.com/firebasejs/9.17.1/firebase-app.js";
import {
    getAuth,
    signInWithPhoneNumber,
    RecaptchaVerifier
} from "https://www.gstatic.com/firebasejs/9.17.1/firebase-auth.js";


const firebaseConfig = {
    apiKey: "AIzaSyAtlMmGTYA7oDRLfddK9R-27ltGn5xFQVE",
    authDomain: "lumit-9a07d.firebaseapp.com",
    projectId: "lumit-9a07d",
    storageBucket: "lumit-9a07d.appspot.com",
    messagingSenderId: "996336680362",
    appId: "1:996336680362:web:8ec9e3dba66477b9e4bfd3"
}
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

window.recaptchaVerifier = new RecaptchaVerifier('recaptcha-container', {
    'size': 'invisible',
    'callback': (response) => {
        // reCAPTCHA solved, allow signInWithPhoneNumber.
        console.log(response)
    }
}, auth);


async function makeVerify(element, type) {
    const phoneNumber = "+16505553434"
    const appVerifier = window.recaptchaVerifier;
    console.log(type)
    let result = await signInWithPhoneNumber(auth, phoneNumber, appVerifier)
        .then((confirmationResult) => {
            // SMS sent. Prompt user to type the code from the message, then sign the
            // user in with confirmationResult.confirm(code).
            window.confirmationResult = confirmationResult;
            // ...
            return confirmationResult.confirm("234923")
        }).catch((error) => {
            // Error; SMS not sent
            // ...
            console.log(error)
        });
    console.log(result._tokenResponse.isNewUser)
}

export {makeVerify}