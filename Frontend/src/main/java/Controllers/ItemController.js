import {item} from "../Module/Item";

$("#getAll").click(function () {

    getAllItem();
});


function bindTrEvents() {
    $('#tblItem>tr').click(function () {
        // $("#txtCustomerID,#txtCustomerName,#txtCustomerAddress,#txtCustomerSalary").css("border", "2px solid blue");
        let code = $(this).children().eq(0).text();
        let description = $(this).children().eq(1).text();
        let qtyOnHand = $(this).children().eq(2).text();
        let unitPrice = $(this).children().eq(3).text();

        //set the selected rows data to the input fields
        $("#inputItemCode").val(code);
        $("#inputItemName").val(description);
        $("#inputItemQty").val(qtyOnHand);
        $("#inputItemPrice").val(unitPrice);
    })
}


$("#btnItemDelete").click(function () {
    let code = $("#inputItemCode").val();
    console.log(code);
    let consent = confirm("Do you want to delete.?");
    if (consent) {
        let response = deleteItem(code);
        alert(response);
        if (response) {
            alert(" Item Not Removed..!");

        } else {
            alert("Item Deleted ");
            clearItemInputFields();
            getAllItem();
        }
    }
});

$("#btnItemUpdate").click(function () {
    let code = $("#inputItemCode").val();
    updateItem(code);
    clearItemInputFields();
});



//clear textField
$("#btn-clear").click(function () {
    clearItemInputFields();

});

// Save Customer
function saveItem() {
    let newItem = Object.assign({}, item);
    newItem.code = $("#inputItemCode").val();
    newItem.description = $("#inputItemName").val();
    newItem.qtyOnHand = $("#inputItemQty").val();
    newItem.unitPrice = $("#inputItemPrice").val();
    console.log(newItem)

    $.ajax({
        url:  "http://localhost:8080/assignment/item",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(newItem),
        success: function (resp, status, xhr) {
            if (xhr.status === 200) {
                alert(resp)
                getAllItem();
            }
        },
        error: function (xhr) {
            alert(xhr.responseText)
        }
    })

}


function getAllItem() {


    $.ajax({
        url: "http://localhost:8080/assignment/item",
        method: "GET",
        success: function (resp, status, xhr){

            if(xhr.status===200){
                console.log(resp)
                let cusBody = $("#tblItem");
                cusBody.empty();
                for(let item of resp){
                    cusBody.append(`
                        <tr>
                            <th scope="row">${item.code}</th>
                            <td>${item.description}</td>
                            <td>${item.qtyOnHand}</td>
                            <td>${item.unitPrice}</td>
                        </tr>`);
                }
            }
        }
    })

}


function deleteItem(code) {

    $.ajax({
        url: "http://localhost:8080/assignment/customer?code=" + code,
        method: "DELETE",
        success: function (resp, status, xhr){

            if(xhr.status===200){
                return true;

            }
        },
        error: function (xhr){
            return false;

        }
    })

}


function searchItem(code) {
    return itemDB.find(function (item) {
        return item.code == code;
    });
}

function updateItem(code) {

    let newItem = Object.assign({}, item);
    newItem.code = $("#inputItemCode").val();
    newItem.description = $("#inputItemName").val();
    newItem.qtyOnHand = $("#inputItemQty").val();
    newItem.unitPrice = $("#inputItemPrice").val();
    console.log(newItem)

    $.ajax({
        url:  "http://localhost:8080/assignment/item",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(newItem),
        success: function (resp, status, xhr) {
            if (xhr.status === 200) {
                alert(resp)
                getAllItem();
            }
        },
        error: function (xhr) {
            alert(xhr.responseText)
        }
    })

}


$(document).on('click', '#ItemTbl > tr', function() {
    let code = $(this).children().eq(0).text();
    let description = $(this).children().eq(1).text();
    let qtyOnHand = $(this).children().eq(2).text();
    let unitPrice = $(this).children().eq(3).text();

    setTextFieldValues(code,description,qtyOnHand,unitPrice);
});

function setTextFieldValues(code,description,qtyOnHand,unitPrice) {
    $("#inputItemCode").val(code);
    $("#inputItemName").val(description);
    $("#inputItemQty").val(qtyOnHand);
    $("#inputItemPrice").val(unitPrice);
}




// getAllItems();
//
// $("#btnItem").click(function () {
//     if (checkAll()){
//         saveItem();
//     }else{
//         alert("Error");
//     }
//
// });
//
// $("#btnItemGetAll").click(function () {
//     getAllItems();
// });
//
// //CLICK
// function bindTrEvents() {
//     $('#tblItem>tr').click(function () {
//         //get the selected rows data
//         let code = $(this).children().eq(0).text();
//         let description = $(this).children().eq(1).text();
//         let qty = $(this).children().eq(2).text();
//         let unitPrice = $(this).children().eq(3).text();
//
//
//         //set the selected rows data to the input fields
//         $("#inputItemCode").val(code);
//         $("#inputItemName").val(description);
//         $("#inputItemQty").val(qty);
//         $("#inputItemPrice").val(unitPrice);
//
//
//     })
// }
//
//
// $("#btnItemDelete").click(function () {
//     let code = $("#inputItemCode").val();
//
//     let consent = confirm("Do you want to delete.?");
//     if (consent) {
//         let response = deleteItem(code);
//         if (response) {
//             alert("Item Deleted");
//             clearItemInputFields();
//             getAllItems();
//         } else {
//             alert("Item Not Removed..!");
//         }
//     }
//
//
// });
//
// $("#btnItemUpdate").click(function () {
//     let code = $("#txtItemCode").val();
//     updateItem(code);
//     clearItemInputFields();
// });
//
// $("#btn-clear").click(function () {
//     clearItemInputFields();
// });
//
//
// function saveItem() {
//     let itemCoad = $("#inputItemCode").val();
//     //check customer is exists or not?
//     if (searchItem(itemCoad.trim()) == undefined) {
//
//         //if the customer is not available then add him to the array
//         let itemCoad = $("#inputItemCode").val();
//         let itemName = $("#inputItemName").val();
//         let itemQtyOnHand = $("#inputItemQty").val();
//         let itemUnitPrice = $("#inputItemPrice").val();
//
//         //by using this one we can create a new object using
//         //the customer model with same properties
//         let newItem = Object.assign({}, item);
//
//         //assigning new values for the customer object
//         newItem.code = itemCoad;
//         newItem.description = itemName ;
//         newItem.qtyOnHand= itemQtyOnHand;
//         newItem.unitPrice = itemUnitPrice;
//
//         //add customer record to the customer array (DB)
//         itemDB.push(newItem);
//         clearItemInputFields();
//         getAllItems();
//
//     } else {
//         alert("Item already exits.!");
//         clearItemInputFields();
//     }
// }
//
// $("#btn-clear").click(function () {
//     clearItemInputFields();
//
// });
//
// function getAllItems() {
//     //clear all tbody data before add
//     $("#tblItem").empty();
//
//     //get all items
//     for (let i = 0; i < itemDB.length; i++) {
//         let code = itemDB[i].code;
//         let description = itemDB[i].description;
//         let qty = itemDB[i].qtyOnHand;
//         let unitPrice = itemDB[i].unitPrice;
//
//         let row = `<tr>
//                      <td>${code}</td>
//                      <td>${description}</td>
//                      <td>${qty}</td>
//                      <td>${unitPrice}</td>
//                     </tr>`;
//
//         // //and then append the row to tableBody
//         $("#tblItem").append(row);
//
//         //invoke this method every time
//         // we add a row // otherwise click
//         //event will not work
//         bindTrEvents();
//
//     }
// }
//
// function deleteItem(code) {
//     for (let i = 0; i < itemDB.length; i++) {
//         if (itemDB[i].code == code) {
//             itemDB.splice(i, 1);
//             return true;
//         }
//     }
//     return false;
// }
//
// function searchItem(code) {
//     return itemDB.find(function (item) {
//         //if the search id match with customer record
//         //then return that object
//         return item.code == code;
//     });
// }
//
// function updateItem(code) {
//     if (searchItem(code) == undefined) {
//         alert("No such Item..please check the code");
//     } else {
//         let consent = confirm("Do you really want to update this Item.?");
//         if (consent) {
//             let item = searchItem(code);
//             //if the customer available can we update.?
//
//             let itemCode = $("#inputItemCode").val();
//             let itemDescription = $("#inputItemName").val();
//             let itemQtyOnHand = $("#inputItemQty").val();
//             let itemUnitPrice = $("#inputItemPrice").val();
//
//             item.code = itemCode;
//             item.description = itemDescription;
//             item.qtyOnHand = itemQtyOnHand;
//             item.unitPrice = itemUnitPrice;
//
//             getAllItems();
//         }
//     }
//
// }
//
//
//
//
//
//
//
//
