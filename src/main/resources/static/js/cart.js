//calculate cart total sum start
function calculateCartTotal() {
    let sum = 0.0;
    let allCartItems = document.getElementsByClassName('shp__pro__details');
    Array.prototype.forEach.call(allCartItems, function (el) {
        let quantity = parseInt(($(el).find('.quantity').text()).match(/(\d+)/));
        let price = parseFloat($(el).find('.shp__price').text());
        sum += (price);
    });
    $('#itemsInTheCart').text(allCartItems.length);
    $('#totalCartSum').text(parseFloat(sum).toFixed(2) + " UAH");
}
//calculate cart total sum end

function addToCartItem(btnObj) {
    let activeItem = $(btnObj).parent().parent().parent().parent().find('.active');
    let jsonObj = JSON.parse($(activeItem).val());
    let packageId = jsonObj["id"];
    let sideBar = $('#sidebarCart');

    $.ajax({
        url: '/addToCart/'+packageId,
        type: 'post',
        success: function(dto){
            sideBar.empty();
            let orderDetails = dto['orderDetails'];
            for (let i=0; i<orderDetails.length; i++){
                let itemsSum = 0.0;
                if (orderDetails[i].packageTypeDto.onSale===true){
                    itemsSum = parseInt(orderDetails[i].count)*parseFloat(orderDetails[i].packageTypeDto.newPrice);
                }else {
                    itemsSum = parseInt(orderDetails[i].count)*parseFloat(orderDetails[i].packageTypeDto.price);
                }
                sideBar.prepend('<div class="shp__single__product">\n' +
                    '                <div class="shp__pro__thumb">\n' +
                    '                    <a href="/singleProduct/'+orderDetails[i].packageTypeDto.productDto.id+'">\n' +
                    '                        <img src="/shop/getImgByProductId/'+orderDetails[i].packageTypeDto.productDto.id+'" alt="product images">\n' +
                    '                    </a>\n' +
                    '                </div>\n' +
                    '                <div class="shp__pro__details">\n' +
                    '                    <h2><a href="/singleProduct/'+orderDetails[i].packageTypeDto.productDto.id+'">'+orderDetails[i].packageTypeDto.productDto.name+' ('+orderDetails[i].packageTypeDto.packSize+' '+orderDetails[i].packageTypeDto.packType+')</a></h2>\n' +
                    '                    <span class="quantity">Кількість: '+orderDetails[i].count+'</span>\n' +
                    '                    <span class="shp__price">'+itemsSum.toFixed(2)+' UAH'+'</span>\n' +
                    '                </div>\n' +
                    '                <div class="remove__btn">\n' +
                    '                    <button onclick="removeItemFromCart('+orderDetails[i].id+',\''+orderDetails[i].orderInfoId+'\')" title="Видалити з кошика"><i class="ti-close"></i></button>\n' +
                    '                </div>\n' +
                    '            </div>');
            }
            calculateCartTotal();
        },
        error: function () {
            alert("Помилка! Спробуйте ще раз");
        }
    });
}

function removeItemFromCart(dtid, uuid) {
    let sideBar = $('#sidebarCart');
    $.ajax({
        url: '/removeFromCart', //?id=/'+parseInt(dtid)+'&uuid='+uuid.trim(),
        data:{
            id: parseInt(dtid),
            uuid: uuid.trim()
        },
        type: 'post',
        success: function(dto){
            sideBar.empty();
            let orderDetails = dto['orderDetails'];
            for (let i=0; i<orderDetails.length; i++){
                let itemsSum = 0.0;
                if (orderDetails[i].packageTypeDto.onSale===true){
                    itemsSum = parseInt(orderDetails[i].count)*parseFloat(orderDetails[i].packageTypeDto.newPrice);
                }else {
                    itemsSum = parseInt(orderDetails[i].count)*parseFloat(orderDetails[i].packageTypeDto.price);
                }
                sideBar.prepend('<div class="shp__single__product">\n' +
                    '                <div class="shp__pro__thumb">\n' +
                    '                    <a href="/singleProduct/'+orderDetails[i].packageTypeDto.productDto.id+'">\n' +
                    '                        <img src="/shop/getImgByProductId/'+orderDetails[i].packageTypeDto.productDto.id+'" alt="product images">\n' +
                    '                    </a>\n' +
                    '                </div>\n' +
                    '                <div class="shp__pro__details">\n' +
                    '                    <h2><a href="/singleProduct/'+orderDetails[i].packageTypeDto.productDto.id+'">'+orderDetails[i].packageTypeDto.productDto.name+' ('+orderDetails[i].packageTypeDto.packSize+' '+orderDetails[i].packageTypeDto.packType+')</a></h2>\n' +
                    '                    <span class="quantity">Кількість: '+orderDetails[i].count+'</span>\n' +
                    '                    <span class="shp__price">'+itemsSum.toFixed(2)+' UAH'+'</span>\n' +
                    '                </div>\n' +
                    '                <div class="remove__btn">\n' +
                    '                    <button onclick="removeItemFromCart('+orderDetails[i].id+',\''+orderDetails[i].orderInfoId+'\')" title="Видалити з кошика"><i class="ti-close"></i></button>\n' +
                    '                </div>\n' +
                    '            </div>');
            }
            calculateCartTotal();
        },
        error: function () {
            alert("Помилка! Спробуйте ще раз");
        }
    });
}