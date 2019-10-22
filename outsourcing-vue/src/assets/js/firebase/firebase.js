var firebaseConfig = {
    apiKey: "AIzaSyBegyZw9_8edPaRQ853NTDRBf7vCr5bccY",
    authDomain: "platform-69c80.firebaseapp.com",
    databaseURL: "https://platform-69c80.firebaseio.com",
    projectId: "platform-69c80",
    storageBucket: "",
    messagingSenderId: "995120855329",
    appId: "1:995120855329:web:61dbabcb0e17e00d"
};
firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();
messaging.usePublicVapidKey("BPSuksnAadwNIcoj65sYZfjtZ_sbda_JzlIaO5IfjCHQmTOAL2phB048djBTKtuNndHuF2oCO44BxofMjoUg_m8");

messaging.onTokenRefresh(() => {
    messaging.getToken().then((refreshedToken) => {
        sendAjax("post", "token", "token=" + currentToken, (response) => { if (response != "success") notify("通知服务注册失败"); });
    }).catch((err) => {
        console.log('Unable to retrieve refreshed token ', err);
    });
});
