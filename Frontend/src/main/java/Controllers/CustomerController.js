// getAllCustomers();

import {customer} from "../Module/Customer.js";



$("#getAll").click(function () {

    getAllCustomers();
});


function bindTrEvents() {
    $('#tblCustomer>tr').click(function () {
        // $("#txtCustomerID,#txtCustomerName,#txtCustomerAddress,#txtCustomerSalary").css("border", "2px solid blue");
        let id = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let address = $(this).children().eq(2).text();
        let salary = $(this).children().eq(3).text();

        //set the selected rows data to the input fields
        $("#txtCustomerID").val(id);
        $("#txtCustomerName").val(name);
        $("#txtCustomerAddress").val(address);
        $("#txtCustomerSalary").val(salary);
    })
}


$("#btnCusDelete").click(function () {
    let id = $("#txtCustomerID").val();
    console.log(id);
    let consent = confirm("Do you want to delete.?");
    if (consent) {
        let response = deleteCustomer(id);
        alert(response);
        if (response) {
            alert(" Customer Not Removed..!");

        } else {
            alert("Customer Deleted ");
            clearCustomerInputFields();
            getAllCustomers();
        }
    }
});

$("#btnUpdate").click(function () {
    let id = $("#txtCustomerID").val();
    updateCustomer(id);
    clearCustomerInputFields();
});



//clear textField
$("#btn-clear1").click(function () {
    clearCustomerInputFields();

});

$("#btnCustomer").click(function () {
    saveCustomer();
    getAllCustomers();
})
// Save Customer
function saveCustomer() {
    let newCustomer = Object.assign({}, customer);
    newCustomer.id = $("#txtCustomerID").val();
    newCustomer.name = $("#txtCustomerName").val();
    newCustomer.address = $("#txtCustomerAddress").val();
    console.log(newCustomer)

    $.ajax({
        url:  "http://localhost:8080/assignment/customer",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(newCustomer),
        success: function (resp, status, xhr) {
            if (xhr.status === 200) {
                alert(resp)
                getAllCustomers();
            }
        },
        error: function (xhr) {
            alert(xhr.responseText)
        }
    })


    // alert("savecust")
    // let customerID = $("#txtCustomerID").val();
    // //check customer is exists or not?
    // if (searchCustomer(customerID.trim()) == undefined) {
    //
    //     let customerID = $("#txtCustomerID").val();
    //     let customerName = $("#txtCustomerName").val();
    //     let customerAddress = $("#txtCustomerAddress").val();
    //     let customerSalary = $("#txtCustomerSalary").val();
    //
    //     let newCustomer = Object.assign({}, customer);
    //
    //     newCustomer.id = customerID;
    //     newCustomer.name = customerName;
    //     newCustomer.address = customerAddress;
    //     newCustomer.salary = customerSalary;
    //
    //     customerDB.push(newCustomer);
    //     clearCustomerInputFields();
    //     getAllCustomers();
    //
    // } else {
    //     alert("Customer already exits.!");
    //     clearCustomerInputFields();
    // }
}


function getAllCustomers() {


    $.ajax({
        url: "http://localhost:8080/assignment/customer",
        method: "GET",
        success: function (resp, status, xhr){

            if(xhr.status===200){
                console.log(resp)
                let cusBody = $("#tblCustomer");
                cusBody.empty();
                for(let customer of resp){
                    cusBody.append(`
                        <tr>
                            <th scope="row">${customer.id}</th>
                            <td>${customer.name}</td>
                            <td>${customer.address}</td>
                        </tr>`);
                }
            }
        }
    })


    // for (let i = 0; i < customerDB.length; i++) {
    //     let id = customerDB[i].id;
    //     let name = customerDB[i].name;
    //     let address = customerDB[i].address;
    //     let salary = customerDB[i].salary;
    //
    //     let row = `<tr>
    //                  <td>${id}</td>
    //                  <td>${name}</td>
    //                  <td>${address}</td>
    //                  <td>${salary}</td>
    //                 </tr>`;
    //
    //     $("#tblCustomer").append(row);
    //     bindTrEvents();
    // }
}


function deleteCustomer(id) {

    $.ajax({
        url: "http://localhost:8080/assignment/customer?id=" + id,
        method: "DELETE",
        success: function (resp, status, xhr){

            if(xhr.status===200){
                return true;
                // console.log(resp)
                // let cusBody = $("#tblCustomer");
                // cusBody.empty();
                // for(let customer of resp){
                //     cusBody.append(`
                //         <tr>
                //             <th scope="row">${customer.id}</th>
                //             <td>${customer.name}</td>
                //             <td>${customer.address}</td>
                //         </tr>`);
                // }
            }
        },
        error: function (xhr){
           return false;

        }
    })

    // for (let i = 0; i < customerDB.length; i++) {
    //     if (customerDB[i].id == id) {
    //         customerDB.splice(i, 1);
    //         return true;
    //     }
    // }
    // return false;
}


function searchCustomer(id) {
    return customerDB.find(function (customer) {
        return customer.id == id;
    });
}

function updateCustomer(id) {

    let newCustomer = Object.assign({}, customer);
    newCustomer.id = $("#txtCustomerID").val();
    newCustomer.name = $("#txtCustomerName").val();
    newCustomer.address = $("#txtCustomerAddress").val();
    console.log(newCustomer)

    $.ajax({
        url:  "http://localhost:8080/assignment/customer",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(newCustomer),
        success: function (resp, status, xhr) {
            if (xhr.status === 200) {
                alert(resp)
                getAllCustomers();
            }
        },
        error: function (xhr) {
            alert(xhr.responseText)
        }
    })
    // if (searchCustomer(id) == undefined) {
    //     alert("No Customer find..please check the ID");
    // } else {
    //     let consent = confirm("Do you really want to update this customer.?");
    //     if (consent) {
    //         let customer = searchCustomer(id);
    //
    //         let customerID = $("#txtCustomerID").val();
    //         let customerName = $("#txtCustomerName").val();
    //         let customerAddress = $("#txtCustomerAddress").val();
    //         let customerSalary = $("#txtCustomerSalary").val();
    //
    //         customer.id = customerID;
    //         customer.name = customerName;
    //         customer.address = customerAddress;
    //         customer.salary = customerSalary;
    //
    //         getAllCustomers();
    //     }
    // }
}




$(document).on('click', '#CustomerTbl > tr', function() {
    let id = $(this).children().eq(0).text();
    let name = $(this).children().eq(1).text();
    let address = $(this).children().eq(2).text();
    let salary = $(this).children().eq(3).text();

    setTextFieldValues(id,name,address,salary);
});

function setTextFieldValues(id, name, address, salary) {
    $("#txtCustomerID").val(id);
    $("#txtCustomerName").val(name);
    $("#txtCustomerAddress").val(address);
    $("#txtCustomerSalary").val(salary);
}






