function searchProduct(){
    let searchString = $('#mainSearchInput').val().trim();
    var table = document.getElementById('mainSearchTable');
    if (searchString.length>=3) {
        console.log(searchString);
        $.ajax({
            method: "post",
            url: "/search",
            contextType: "application/json",
            data: {
                name: searchString
            },
            success: function (result) {
                console.log(result);
                if (result.length===0){
                    $('#mainSearchResults').hide();
                }else {
                    table.innerHTML = "";
                    for (let i =0;i<result.length;i++){
                        var row = table.insertRow(i);
                        var cell1 = row.insertCell(0);
                        var cell2 = row.insertCell(1);

                        cell1.innerHTML = '<img src="/shop/getImgByProductId/'+result[i].id+'"/>';
                        cell2.innerHTML = '<a href="/singleProduct/'+result[i].id+'">'+result[i].name+'</a>';
                    }
                    $('#mainSearchResults').show();
                }
            },
            error: function () {
                console.log("Error!");
            }
        }); //ajax end
    }else {
        $('#mainSearchResults').hide();
    }
}

function setSearchDelay() {
    this.clearTime()
    this.delaySearch = setTimeout(() => {
        this.searchProduct()
    }, 300)
}

function  clearTime () {
    if (this.delaySearch) {
        clearInterval(this.delaySearch)
    }
}

function hideResults() {
    setTimeout(function(){$('#searchResults').hide()}, 1000);
}