const pay_phone_btn = document.querySelector('.pay_phone_btn');
pay_phone_btn.addEventListener('click', function () {
    const productNameEls = document.querySelectorAll('.product_name');
    const productName = productNameEls[0].innerHTML + ' 외 ' + (productNameEls.length - 1) + '건';
    const totalPrice = document.querySelector('.total_price').innerHTML;
    const phone = document.querySelector('.phone').innerHTML;
    const address = document.querySelector(".address").innerHTML;
    const cart_id_list = document.querySelector('.cart_id_list').value;

    IMP.init("imp82217082");

    IMP.request_pay({
            pg: "danal",
            pay_method: "phone",
            merchant_uid: "merchant_" + new Date().getTime(),
            name: productName,
            amount: totalPrice,
            buyer_tel: phone,
        },
        function (resp) {
            const params = {
                params: {
                    imp_uid: resp.imp_uid,
                    merchant_uid: resp.merchant_uid,
                    pay_method : resp.pay_method,
                    name : encodeURIComponent(productName),
                    price : resp.paid_amount,
                    status : resp.status,
                    address : encodeURIComponent(address),
                    cart_id_list : encodeURIComponent(cart_id_list)
                }
            }
            axios.get("/payment/add", params)

                .then(res => {
                    alert('결제가 완료 되었습니다. 결제 확인 페이지로 이동합니다.');
                    location.href="/payment/list"
                })
                .catch(err => {
                    console.log(err);
                })
        });
})