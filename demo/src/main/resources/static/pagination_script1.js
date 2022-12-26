const table = document.getElementById('table');

const user_list = [];

const pagination_element = document.getElementById('pagination');

let current_page = 1;
let rows = 6;

function FillUserList(table, user_list) {
    for(var i = 0; i < table.rows.length; i++) {
        var table_row = [];
        var objCells = table.rows.item(i).cells;
        for(var j = 0; j < objCells.length; j++) {
            table_row.push(objCells[j].innerText);
        }
        user_list.push(table_row);
    }
}

function FillTable(list, container, rows_per_page, page) {

    container.innerHTML = '';
    page--;

    let start = rows_per_page*page;
    let end = start + rows_per_page;
    let paginatedItems = user_list.slice(start, end);

    container.appendChild(document.createElement('tr'));
    for(let x = 0; x < user_list[0].length; x++){
        let table_cell = document.createElement('th');

        table_cell.innerText = user_list[0][x];
        container.appendChild(table_cell);
    }

    for(let i = 1; i < paginatedItems.length; i++) {
        container.appendChild(document.createElement('tr'));
        for(let j = 0; j < paginatedItems[i].length - 2; j++) {

            let table_cell = document.createElement('td');

            table_cell.innerText = paginatedItems[i][j];
            container.appendChild(table_cell);
        }

        /*<![CDATA[*/

                   let cell_icon1 = document.createElement('td');

                   cell_icon1.classList.add('icons');

                   var link = /*[['/users/4']]*/ '/users/' + paginatedItems[i][0] + '/edit';

                   cell_icon1.innerHTML = '<a href="' + link + '"><i class="fa-solid fa-pen-to-square fa-2xl"></i></a>';

                   container.appendChild(cell_icon1);

                   let cell_icon2 = document.createElement('td');

                   cell_icon2.classList.add('icons');

                   link = /*[['/users/4']]*/ '/users/' + paginatedItems[i][0] + '/delete';

                   cell_icon2.innerHTML = '<a href="' + link + '"><i class="fa-solid fa-trash fa-2xl delete-icon"></i></a>';

                   container.appendChild(cell_icon2);

        /*]]>*/
    }
}

function SetupPagination(items, container, rows_per_page) {

    container.innerHTML = "";

    let page_count = Math.ceil(items.length / rows_per_page);

    let left_button = document.createElement('button');
    left_button.innerText = "<";
    left_button.style = 'margin-right: 15px;';

    left_button.addEventListener('click', function() {
        if(current_page > 1) {
            current_page--;

            let current_btn = document.querySelector('.pagination button.active')
            current_btn.classList.remove('active');

            let target_btn = document.getElementById(current_page);

            target_btn.classList.add('active');
        }

        FillTable(user_list, table, rows, current_page);
    });

    container.appendChild(left_button);

    for(let i = 1; i < page_count + 1; i++) {
        let btn = PaginationButton(i, items);
        container.appendChild(btn);
    }

    let right_button = document.createElement('button');
    right_button.innerText = ">";
    right_button.style = 'margin-left: 15px;';

    right_button.addEventListener('click', function() {
        if(current_page < (Math.ceil(user_list.length / rows))) {
            current_page++;

            let current_btn = document.querySelector('.pagination button.active')
            current_btn.classList.remove('active');

            let target_btn = document.getElementById(current_page);

            target_btn.classList.add('active');
        }

        FillTable(user_list, table, rows, current_page);
    });

    container.appendChild(right_button);
}

function PaginationButton(page, items) {

    let btn = document.createElement('button');
    btn.innerText = page;
    btn.id = page;

    btn.addEventListener('click', function() {
        current_page = page;
        FillTable(user_list, table, rows, current_page);

        let current_btn = document.querySelector('.pagination button.active')
        current_btn.classList.remove('active');

        btn.classList.add('active');
    });

    if(current_page == page) btn.classList.add('active');

    return btn;
}

function search() {

    console.log('aLSJDLAKSSAJK');
            const filter_text = document.getElementById('search').innerText;
            console.log(filter_text);
                const filtered_user_list = [];
                    for(var i = 0; i < user_list.length; i++) {
                        for(var j = 0; j < user_list[i].length; j++) {
                            if(user_list[i][j].includes(filter_text)) {
                                filtered_user_list.push(user_list[i]);
                                break;
                            }
                        }
                    }

    FillTable(filtered_user_list, table, rows, current_page);

}

FillUserList(table, user_list);
FillTable(user_list, table, rows, current_page);
SetupPagination(user_list, pagination_element, rows);


