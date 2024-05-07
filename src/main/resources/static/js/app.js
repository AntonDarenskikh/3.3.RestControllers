let roles = '';
let output = '';
const usersUrl = 'http://localhost:8080/api/users';
let table = document.getElementById('my-body1');
let addForm = document.forms.addForm;
let editForm = document.forms.editForm;
let deleteForm = document.forms.deleteForm;
let modalEdit = document.getElementById('defaultEdit')
let modalDelete = document.getElementById('defaultDelete')
let roleSelect = '';
getRoles();

async function getRoles() {
    let preroles = await fetch(`http://localhost:8080/api/roles`);
    let roles = await preroles.json();
    Array.from(roles).forEach(role => {
        roleSelect += `<option value=${role.name}>${role.name}</option>`;
    })
    addForm.roles.innerHTML = roleSelect;
}

const userFetchInfo = {
    head: {
        'Content-Type': 'application/json',
    },
}
const addUserToTable = (user) => {
    output += `
        <tr id = 'tableString${user.id}'>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.authorities.map(role => " " + role.name)}</td>
            <td>
                <a type="button" data-id="${user.id}" class="btn btn-info text-white btn-sm edit-button" data-bs-toggle="modal" role="button">
                    Edit
                </a>
            </td>
            <td>
                <a type="button" data-id="${user.id}" class="btn btn-danger btn-sm delete-button" data-bs-toggle="modal" role="button">
                    Delete
                </a>
            </td>
            </tr>
        `;
    table.innerHTML=output;
    makeButtons();

}
async function loadUserTable() {
    let response = await fetch(usersUrl);
    table.innerHTML='';
    output='';

    if (response.status == 200) {
        let users = await response.json();
        Array.from(users).forEach(user => {
            addUserToTable(user);
        });

    }
    throw new Error(response.status);
}
async function loadAboutUser() {
    let response = await fetch('http://localhost:8080/api/users/current');
    if (response.status == 200) {
        let user = await response.json();
        output += `
        <tr class = "table1">
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(role => " " + role.name)}</td></tr>
        `;
        table.innerHTML=output;
    }
    throw new Error(response.status);
}
async function loadNavbar() {
    let navbarEmail = document.getElementById('navbar-email');
    let navbarRoles = document.getElementById('navbar-roles');
    let response = await fetch('http://localhost:8080/api/users/current');
    if (response.status == 200) {
        let user = await response.json();
        navbarEmail.innerHTML=`${user.email}`;
        Array.from(user.authorities).forEach(authoritie => {
            roles += `${authoritie.name} `
            navbarRoles.innerHTML=roles;
        });
        return user;
    }
    throw new Error(response.status);
}
async function makeButtons() {
    let editButtons  = document.querySelectorAll('.edit-button')
    let deleteButtons  = document.querySelectorAll('.delete-button')
    editButtons.forEach(button => {
        button.addEventListener('click', () => {
            let id = button.getAttribute('data-id')
            openEditModal(id)
        })
    })

    deleteButtons.forEach(button => {
        button.addEventListener('click', () => {
            let id = button.getAttribute('data-id')
            openDeleteModal(id)
        })
    })
}
async function openEditModal(id) {
    let response = await fetch(`http://localhost:8080/api/users/${id}`);
    let user = await response.json();

    editForm.editLastName.value = user.lastName;
    editForm.editFirstName.value = user.firstName;
    editForm.editAge.value = user.age;
    editForm.editId.value = user.id;
    editForm.editPassword.value = user.password;
    editForm.editEmail.value = user.email;
    editForm.roles.innerHTML = roleSelect;

    modalEdit.style.display = 'block';

    const closeButton1 = document.getElementsByClassName('close')[0];
    closeButton1.addEventListener('click', () => {
        modalEdit.style.display = 'none';
    });

    const closeButton = document.getElementsByClassName('close')[1];
    closeButton.addEventListener('click', () => {
        modalEdit.style.display = 'none';
    });
}
async function openDeleteModal(id) {
    let response = await fetch(`http://localhost:8080/api/users/${id}`);
    let user = await response.json();

    deleteForm.deleteLastName.value = user.lastName;
    deleteForm.deleteFirstName.value = user.firstName;
    deleteForm.deleteAge.value = user.age;
    deleteForm.deleteId.value = user.id;
    deleteForm.deletePassword.value = user.password;
    deleteForm.deleteEmail.value = user.email;
    deleteForm.roles.innerHTML = roleSelect;

    modalDelete.style.display='block';

    const closeButton1 = document.getElementsByClassName('button-close')[0];
    closeButton1.addEventListener('click', () => {
        modalDelete.style.display = 'none';
    });

    const closeButton = document.getElementsByClassName('button-close')[1];
    closeButton.addEventListener('click', () => {
        modalDelete.style.display = 'none';
    });
}
addForm.addEventListener('submit', (event) => {
    event.preventDefault();

    let roles = [];
    for (let i = 0; i < addForm.roles.size; i++) {
        if (addForm.roles.options[i].selected){
            roles.push({'name' : addForm.roles[i].value})
        }
    }

    const user = {
        firstName: addForm.firstName3.value,
        lastName: addForm.lastName3.value,
        password: addForm.userPassword.value,
        age: addForm.userAge.value,
        email: addForm.userLogin.value,
        roles: roles,
    };
    fetch(usersUrl, {
        method: 'POST',
        headers: userFetchInfo.head,
        body: JSON.stringify(user),
    })
        .then(res => res.json())
        .then(async data => {
            await addUserToTable(data)
        })

})
editForm.addEventListener('submit', (event) => {
    event.preventDefault();
    let roles = [];
    for (let i = 0; i < editForm.roles.size; i++) {
        if (editForm.roles.options[i].selected){
            roles.push({'name' : editForm.roles[i].value})
        }
    }

    const user = {
        id: editForm.editId.value,
        firstName: editForm.editFirstName.value,
        lastName: editForm.editLastName.value,
        password: editForm.editPassword.value,
        age: editForm.editAge.value,
        email: editForm.editEmail.value,
        roles: roles,
    };

    fetch(usersUrl, {
        method: 'PATCH',
        headers: userFetchInfo.head,
        body: JSON.stringify(user),
    })
        .then(res => res.json())
        .then(async data => {
            await loadUserTable('http://localhost:8080/api/users');
        })
    modalEdit.style.display = 'none';
})
deleteForm.addEventListener('submit', (event) => {
    event.preventDefault();

    fetch(usersUrl+"/"+deleteForm.deleteId.value, {
        method: 'DELETE',
        headers: userFetchInfo.head,
    })
        .then(res => res.json())
        .then(async data => {
            await loadUserTable('http://localhost:8080/api/users');
        })
    modalDelete.style.display = 'none';
})
