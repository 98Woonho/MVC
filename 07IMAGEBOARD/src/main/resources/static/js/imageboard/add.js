const formData = new FormData();	//폼관련 정보 저장

const uploadBox_el = document.querySelector('.upload-box');
//dragenter / dragover /dragleave / drop

uploadBox_el.addEventListener('dragenter', function (e) {
    e.preventDefault();
    console.log("dragenter...");
});
uploadBox_el.addEventListener('dragover', function (e) {
    e.preventDefault();
    uploadBox_el.style.opacity = '0.5';
    console.log("dragover...");

});
uploadBox_el.addEventListener('dragleave', function (e) {
    e.preventDefault();
    uploadBox_el.style.opacity = '1';
    console.log("dragleave...");

});

//----------------------------------------------
//
//----------------------------------------------
uploadBox_el.addEventListener('drop', function (e) {
    e.preventDefault();
    console.log("drop...");
    console.log(e);
    console.log(e.dataTransfer);
    console.log(e.dataTransfer.files[0]);

    // 유효성 체크
    const imgFiles = Array.from(e.dataTransfer.files).filter(f => f.type.startsWith('image/')); // type이 image/로 시작하는 파일들만 가져와서 배열로 구성
    if (imgFiles.length === 0) {
        alert("이미지 파일만 가능합니다.");
        return false;
    }

    // 이미지 파일 용량 제한
    imgFiles.forEach(file => {
        if (file.size > (1024 * 1024 * 5)) {
            alert("파일 하나당 최대 사이즈는 5MB이하여야 합니다.")
        }
    })

    const reader = new FileReader(); // FileReader
    for (var file of imgFiles) {
        reader.readAsDataURL(file); // reader에 file 정보를 넣어줌.
        reader.onload = function (e) { // preview 태그에 이미지가 업로드 되었을 때 동작 함수
            const preview = document.querySelector('#preview');
            const imgEl = document.createElement('img');
            imgEl.setAttribute('src', e.target.result);
            preview.appendChild(imgEl); // preview의 자식으로 imgEl 태그 생성
        }
        formData.append('files', file);
    }
});

const add_product_btn_el = document.querySelector('.add_product_btn');
add_product_btn_el.addEventListener('click', function () {
    const seller = document.imageform.seller.value;
    const productname = document.imageform.productname.value;
    const category = document.imageform.category.value;
    const brandname = document.imageform.brandname.value;
    const price = document.imageform.price.value;
    const itemdetails = document.imageform.itemdetails.value;
    const amount = document.imageform.amount.value;
    const size = document.imageform.size.value;

    formData.append('seller', seller);
    formData.append('productname', productname);
    formData.append('category', category);
    formData.append('brandname', brandname);
    formData.append('price', price);
    formData.append('itemdetails', itemdetails);
    formData.append('amount', amount);
    formData.append('size', size);

    axios.post('/imageboard/add', formData, {headers: {'Content-Type': 'multipart/form-data'}})
        .then(res => {
            console.log(res);
            alert("물품 등록을 완료 하였습니다.")
            location.href = "/imageboard/list";
        })
        .catch(err => {
            console.log(err);
        })
})