
function activateButton(radio) {
    const btn = document.querySelector('#users_check');
    if(radio.checked) {
        btn.disabled = false;
    }
    else {
        btn.disabled = true;
    }
}


function delay(time) {
  return new Promise(resolve => setTimeout(resolve, time));
}

function smsSend() {

    const user_count = 10000;
    let sent_sms_count = 0;
    let new_sms_count = 0;

    let initial_text1 = document.getElementById("sms_count").innerText + ' ';
    let initial_text2 = document.getElementById("send_percent").innerText + ' ';

    for(let i = 1; i <= 10; i++) {

        setTimeout(function(){
            new_sms_count = Math.floor(Math.random() * 157 + 94);
            console.log('new count ' + new_sms_count);
            (sent_sms_count + new_sms_count) > user_count ? sent_sms_count = 10000 : sent_sms_count += new_sms_count;
            console.log('sent ' + sent_sms_count);
            document.getElementById("sms_count").innerText = initial_text1 + sent_sms_count;
            document.getElementById("send_percent").innerText = initial_text2 + (i*10) + '%';
        },2000);
    }

}

function setTemplate() {



}





