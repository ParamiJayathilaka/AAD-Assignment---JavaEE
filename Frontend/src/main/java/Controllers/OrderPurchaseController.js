

import {item} from "../Module/Item";
// import(orderDetails) from "../Module/Order"

$("#getAll").click(function () {

    getAllOrder();
});


function bindTrEvents() {
    $('#tblOrder>tr').click(function () {
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
























// // customer side
//
// let selectElement = document.getElementById("CustominputState");
// // getAllCustomer();
// // function getAllCustomer() {
// //     customerDB.forEach(function(customer) {
// //         let option = document.createElement("option");
// //         option.value = customer.id;
// //         option.textContent = customer.id;
// //         selectElement.appendChild(option);
// //     });
// // }
//
// let CustomerIdShirOrder;
// selectElement.addEventListener("change", function () {
//     let selectedId = selectElement.value;
//     let selectedCustomer = customerDB.find(function(customer) {
//         return customer.id === selectedId;
//     });
//     if (selectedCustomer) {
//         $("#custIdSetOrder").val(selectedCustomer.id);
//         $("#custNameSetOrder").val(selectedCustomer.name);
//         $("#custAddressSetOrder").val(selectedCustomer.address);
//         $("#custSalarySetOrder").val(selectedCustomer.salary);
//         CustomerIdShirOrder = selectedCustomer.id;
//     }
// });
//
// // item side
//
// let selectItemElement = document.getElementById("IteminputState");
// let itemCode;
// let itemName;
//
// itemDB.forEach(function(item) {
//     let option = document.createElement("option");
//     option.value = item.code;
//     option.textContent = item.code;
//     selectItemElement.appendChild(option);
// });
//
// let itemCodetoOrder;
// let itemNametoOrder;
// let itemPricetoOrder;
// let ChoiceElementOrder = document.getElementById("ChoiceQTYOrder");
// const defaultArrayToSecondItem = [];
// selectItemElement.addEventListener("change", function () {
//     let selectedId = selectItemElement.value;
//     let selectedItem = itemDB.find(function(item) {
//         return item.code === selectedId;
//     });
//     if (selectedItem) {
//         CheckQTY(selectedItem.qtyOnHand);
//         $("#ItemIdSetOrder").val(selectedItem.code);
//         $("#ItemNameSetOrder").val(selectedItem.description);
//         $("#ItemPriceSetOrder").val(selectedItem.unitPrice);
//         $("#ItemQTYSetOrder").val(selectedItem.qtyOnHand);
//
//         itemCodetoOrder = selectedItem.code;
//         itemNametoOrder = selectedItem.description;
//         itemPricetoOrder = selectedItem.unitPrice;
//     }
// });
//
// let NotablesetRound=0;
// function getAllItemTOOrder() {
//     let newItemtoOrder = Object.assign({}, itemToOrder);
//     let totalItemPrice = itemPricetoOrder * ChoiceElementOrder.value;
//     let existingItemIndex = defaultArrayToSecondItem.findIndex(item => item.itemCode === itemCodetoOrder);
//     if (existingItemIndex !== -1) {
//         defaultArrayToSecondItem[existingItemIndex].itemQTYChoice = ChoiceElementOrder.value;
//         defaultArrayToSecondItem[existingItemIndex].totalPrice = totalItemPrice;
//
//     } else {
//         newItemtoOrder.itemCode = itemCodetoOrder;
//         newItemtoOrder.itemName = itemNametoOrder;
//         newItemtoOrder.itemPrice = itemPricetoOrder;
//         newItemtoOrder.itemQTYChoice = ChoiceElementOrder.value;
//         newItemtoOrder.totalPrice = totalItemPrice;
//         defaultArrayToSecondItem.push(newItemtoOrder);
//         NotablesetRound++;
//         // alert(NotablesetRound);
//     }
//     getAllItemSetTableArray();
// }
//
// function getAllItemSetTableArray() {
//     $("#TBodyOrder").empty()
//     for (let i = 0; i < defaultArrayToSecondItem.length; i++) {
//         let id = defaultArrayToSecondItem[i].itemCode;
//         let name = defaultArrayToSecondItem[i].itemName;
//         let price = defaultArrayToSecondItem[i].itemPrice;
//         let QTY = defaultArrayToSecondItem[i].itemQTYChoice;
//         let total = defaultArrayToSecondItem[i].totalPrice;
//         let row = `<tr>
//                      <td>${id}</td>
//                      <td>${name}</td>
//                      <td>${price}</td>
//                      <td>${QTY}</td>
//                      <td>${total}</td>
//                     </tr>`;
//         $("#TBodyOrder").append(row);
//         calculateTotalPrice();
//     }
// }
//
// //purchase order
// let totalPriceSum2;
// function calculateTotalPrice() {
//     let totalPriceSum = 0;
//     for (let i = 0; i < defaultArrayToSecondItem.length; i++) {
//         totalPriceSum += defaultArrayToSecondItem[i].totalPrice;
//     }
//     document.getElementById("lableTotPrice").innerHTML = totalPriceSum;
//     document.getElementById("lableSubTotal").innerHTML = totalPriceSum;
//     totalPriceSum2=totalPriceSum;
// }
//
// let inputCash = document.getElementById("inputCash");
// let cashLOwMasse = document.getElementById("cashShow");
//
// inputCash.addEventListener("keyup", function () {
//     inputCashCheck();
// });
//
// // discount
// let discount = document.getElementById("discount");
// discount.addEventListener("keyup", function (){
//     let discountValue = discount.value;
//     let discountAmount = (discountValue / 100) * totalPriceSum2;
//     let discountedPrice = totalPriceSum2 - discountAmount;
//     document.getElementById("lableSubTotal").innerHTML = discountedPrice;
//     let balance = inputCash.value-discountedPrice;
//     $("#BalanceInput").val(balance);
// });
//
// function ItemQTYLower(orderIDstor) {
//     for (let i = 0; i < defaultArrayToSecondItem.length; i++) {
//         let defaultArrayItemCode = defaultArrayToSecondItem[i].itemCode;
//         let defaultArrayItemQTY = defaultArrayToSecondItem[i].itemQTYChoice;
//
//         for (let k = 0; k < itemDB.length; k++) {
//             let ItemCode = itemDB[k].code;
//             if (defaultArrayItemCode === ItemCode) {
//                 let itemQtyOnHand = itemDB[k].qtyOnHand;
//                 let lowQTYUpdate = itemQtyOnHand - defaultArrayItemQTY;
//
//                 itemDB[k].qtyOnHand = lowQTYUpdate;
//
//                 // console.log("Code: " + itemDB[k].code);
//                 // console.log("Description: " + itemDB[k].description);
//                 // console.log("Qty on Hand: " + lowQTYUpdate);
//                 // console.log("Unit Price: " + itemDB[k].unitPrice);
//                 // console.log("\n");
//             }
//         }
//     }
//     setOrderValue(orderIDstor);
// }
//
// // arry set value
// // const secondRoundArry = [];
//
// function setOrderValue(orderIDstor) {
//     // orderDB.length=0;
//     // orderDB.orderDetails=0
//     let orderId = orderIDstor;
//     let date = $("#date").val();
//     let custId = $("#custIdSetOrder").val();
//
//     let order = {
//         oid: orderId,
//         date: date,
//         customerID: custId,
//         orderDetails: []
//     };
//
//     for (let i = 0; i < defaultArrayToSecondItem.length; i++) {
//         let id = defaultArrayToSecondItem[i].itemCode;
//         let name = defaultArrayToSecondItem[i].itemName;
//         let price = defaultArrayToSecondItem[i].itemPrice;
//         let QTY = defaultArrayToSecondItem[i].itemQTYChoice;
//         let total = defaultArrayToSecondItem[i].totalPrice;
//
//         order.orderDetails.push({
//                 oid: orderId,
//                 code: id,
//                 qty: QTY,
//                 unitPrice: total
//             }
//         );
//     }
//
//     orderDB.push(order);
//     defaultArrayToSecondItem.length=0;
//     // orderDB.forEach(function (order) {
//     //     console.log("Order ID: " + order.oid);
//     //     console.log("Date: " + order.date);
//     //     console.log("Customer ID: " + order.customerID);
//     //
//     //     order.orderDetails.forEach(function (detail) {
//     //         console.log("Order Detail Code: " + detail.oid);
//     //         console.log("Order Detail Code: " + detail.code);
//     //         console.log("Quantity: " + detail.qty);
//     //         console.log("Unit Price: " + detail.unitPrice);
//     //     });
//     // });
//
//     // for (let i = 0; i < secondRoundArry.length; i++) {
//     //     console.log(secondRoundArry[i]);
//     // }
//     allemtyset();
// }
//
//
//
// const totalArry = [];
// let ChoiceElement6 = document.getElementById("OrderId");
// ChoiceElement6.addEventListener("keyup", function () {
//     let inputOrd = ChoiceElement6.value;
//     $("#TBodyOrder").empty();
//
//     for (let i = 0; i < orderDB.length; i++) {
//         let order = orderDB[i];
//         if (order.oid === inputOrd) {
//             let orderDetails = order.orderDetails;
//             let totalOrderPrice = 0;
//
//             for (let j = 0; j < orderDetails.length; j++) {
//                 let code = orderDetails[j].code;
//                 let QTY = orderDetails[j].qty;
//                 let unitPrice = orderDetails[j].unitPrice;
//
//                 for (let k = 0; k < itemDB.length; k++) {
//                     let realItemid = itemDB[k].code;
//
//                     if (realItemid === code) {
//                         let realItemname = itemDB[k].description;
//                         let realItemPrice = itemDB[k].unitPrice;
//                         let row = `<tr>
//                             <td>${code}</td>
//                             <td>${realItemname}</td>
//                             <td>${realItemPrice}</td>
//                             <td>${QTY}</td>
//                             <td>${unitPrice}</td>
//                         </tr>`;
//                         $("#TBodyOrder").append(row);
//
//                         totalOrderPrice += unitPrice;
//                     }
//                 }
//             }
//
//             $("#lableTotPrice").text(totalOrderPrice);
//             $("#lableSubTotal").text(totalOrderPrice);
//
//         }
//     }
// });
//
// //    delete table value
// $(document).ready(function () {
//     $('#clickTable').on('click', 'tr', function () {
//         var userConfirmed = confirm("Do you want to Remove ?");
//
//         if (userConfirmed) {
//             alert("Success");
//             let Itemcode = $(this).children().eq(0).text();
//
//             for (let i = 0; i < defaultArrayToSecondItem.length; i++) {
//                 if (defaultArrayToSecondItem[i].itemCode == Itemcode) {
//                     defaultArrayToSecondItem.splice(i, 1);
//                     $("#lableTotPrice").text("0");
//                     $("#lableSubTotal").text("0");
//                     getAllItemSetTableArray();
//                 }
//             }
//         } else {
//
//         }
//
//     });
// });