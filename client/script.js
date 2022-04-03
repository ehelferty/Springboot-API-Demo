
var selSortByList;
var pageNumberInput;
var pageNumber;
var sortBy;

//Meta Data table variables
var metaPageNumber
var metaPagesAvailable
var metaPageSize
var metaItemsReturned
var metaSortBy

let url = "http://localhost:8080/api/guests";


window.onload = () => {
    document.getElementById("getData").onclick = getData


    trResponseRow = document.getElementById("trResponseRow");
    selSortByList = document.getElementById("sortByList");
    pageNumberInput = document.getElementById("pageNumInput");

    //Meta Data table variables
    metaPageNumber = document.getElementById("metaPageNumber")
    metaPagesAvailable = document.getElementById("metaPagesAvailable")
    metaPageSize = document.getElementById("metaPageSize")
    metaItemsReturned = document.getElementById("metaItemsReturned")
    metaSortBy = document.getElementById("metaSortBy")

    fetch(url)
        .then(response => response.json())
        .then(json => {
            pageNumberInput.setAttribute("max", json.totalPages)
        })
        .catch(error => alert(error))

    selSortByList.onchange = () => {
        setSortSelection(selSortByList.value)
    }

}

/**
 * Sets the sortBy variable to the choice selected by user via sortByList HTML select dropdown
*/
function setSortSelection(sortSelection) {
    sortBy = sortSelection
}

/**
 * When GET button is clicked, getData() fetches the guest details using parameters specified by user input HTML elements.
*/
function getData() {
    pageNumber = document.getElementById("pageNumInput").value

    fetch(url + "?page=" + pageNumber + "&sort=" + sortBy)
        .then(response => response.json())
        .then(json => {
            displayGuests(json.content)
            displayMetaData(json)
        })
        .catch(error => alert(error))
}

/**
 * Receives the guests json data fetched in getData() function, and outputs values into HTML elements 
 */
function displayGuests(guests) {
    trResponseRow.innerHTML = ""

    let keys = Object.keys(guests)

    keys.forEach(keys => {
        trResponseRow.innerHTML +=
            `<tr>
                <td>${guests[keys].id}</td>
                <td>${guests[keys].firstName}</td>
                <td>${guests[keys].lastName}</td>
                <td>${guests[keys].emailAddress}</td>
                <td>${guests[keys].address}</td>
                <td>${guests[keys].country}</td>
                <td>${guests[keys].phoneNumber}</td>
            </tr>`
    })
}

/**
 * Receives jsonData from the fetch request and outputs the meta data values to the html page.
 * @param {*} jsonData 
 */
function displayMetaData(jsonData) {
    metaItemsReturned.innerText = jsonData.totalElements
    metaPageNumber.innerText = jsonData.pageable.pageNumber
    metaPageSize.innerText = jsonData.size
    metaPagesAvailable.innerText = jsonData.totalPages
    metaSortBy.innerText = sortBy
}
