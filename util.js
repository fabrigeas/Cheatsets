/** This script contains some useful js functions.
 * Author: Fabrice Feugang Kemegni
 */

/** Makes a HTTP request and returns a Promis
 * @param {String} url the backend url
 * @param {String} method GET, POST ...
 * @param {Object} data The Object
 */
function httpRequest(url, method, data) {
  return fetch(url, {
    body: JSON.stringify(data),
    cache: "no-cache",
    credentials: "same-origin",
    headers: {
      // 'user-agent': 'Mozilla/4.0 MDN Example',
      "content-type": "application/json",
    },
    method: method,
    mode: "cors",
    redirect: "follow",
    referrer: "no-referrer",
  }).then((response) => response.json());
}

/** Make a get request. Returns a promise
 * @param {String} url
 */
function get(url) {
  return fetch(url).then((response) => response.json());
}

/** Event handler for image selected
 * This function takes the selected image and places it on an image preview
 * @param {event} event the input.file
 *
 * This function requires a file input and a img, add the id of your image to the input data.preview attribute
 *  * Example
 *  * input(type='file' accept="image/*" data-preview="book-detail-preview" onchange='onImageSelectionComplete(event)')
 *  * img(id="book-detail-preview.preview")
 */
function onImageSelectionComplete(event) {
  let fileReader = new FileReader(),
    file = event.target.files[0];

  if (file) {
    fileReader.readAsDataURL(file);
  }

  fileReader.onload = function (e) {
    document.getElementById(event.target.dataset.preview).src = e.target.result;
  };
}

/** This function takes a file input, and uploads the input's files to the server
 *
 * @param {HTMLElement} input the Input containing the files: o ..* files
 * @param {String} path The destination folder, where the files should be saved. The backend will create one if it doesn't exist
 */
function uploadFiles(input, path) {
  let formData = new FormData();

  for (let file of input.files) {
    formData.append("file", file);
  }

  formData.append("path", path);

  return new Promise(function (resolve, reject) {
    $.ajax({
      type: "POST",
      url: "/files",
      processData: false,
      contentType: false,
      data: formData,
      succes: resolve,
      error: reject,
    });
  });
}

/** Returns and object containing all the attributes of the form
 * @param {HTMLElement} form The html form:
 */
function formToObject(form) {
  let item = {};

  for (let element of form.querySelectorAll("[name]")) {
    if (element.type == "checkbox") {
      item[element.name] = element.checked;
    } else if (element.type == "file") {
      let attribute = [element.name];

      item[attribute] = [];
      for (let file of element.files) {
        item[attribute].push(file.name);
      }
    } else {
      item[element.name] = element.value;
    }
  }

  return item;
}

/** Fills the form's inputs with the values of the object
 * @param {HTMLElement} form The form to be filled: documen.querySelector("selector")
 * @param {Object} object The Object containing the values: {,,}
 */
function populateForm(form, object) {
  for (let attribute in object) {
    let element = form.querySelector(`[name=${attribute}]`);

    if (element) {
      if (element.type == "select-one") {
        let e = element.querySelector(`[value='${object[attribute]}']`);
        if (e) {
          e.selected = true;
        }
      } else if (element.type == "checkbox") {
        element.checked = object[attribute];
      } else {
        if (element.type != "file") {
          element.value = object[attribute];
        }
      }
    }
  }
}

/** This function clears all the values of the form by setting the to null
 * @param {HTMLElement} form The form to be cleard
 */
function resetForm(form) {
  for (let element of form.querySelectorAll("[name]")) {
    element.value = null;
  }
}

/** Makes an ajax call and returns a promise
 *
 * @param {String} url
 * @param {String} type
 * @param {JSON} data
 * @param {function} callback
 */
function ajax(url, type, data) {
  return new Promise(function (resolve, reject) {
    $.ajax({
      url: url,
      type: type,
      data: data,
      success: resolve,
      error: reject,
    });
  });
}

/** Get an entry document.cookie
 *
 * @param {String} name
 *
 * @returns {String}
 */
const readCookie = (name) => {
  let nameEQ = name + "=",
    ca = document.cookie.split(";");

  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == " ") {
      c = c.substring(1, c.length);
    }
    if (c.indexOf(nameEQ) == 0) {
      return decodeURIComponent(c.substring(nameEQ.length, c.length)).replace(
        "j:",
        ""
      );
    }
  }
  return null;
};

/** Add an entry to the cookies. This can be retrived using @readCookie
 * @param {String} name The attribute name
 * @param {*} value can be any data type: String, number, boolean ...
 * @param {Date} days a Date or number
 */
const createCookie = (name, value, days) => {
  let date;
  let expires = "";

  if (days) {
    date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    expires = "; expires=" + date.toGMTString();
  }

  document.cookie = name + "=" + value + expires + "; path=/";
};

/** Helper function to read and parse document.cookie
 *
 * @param {String} cname
 *
 * @return {String}
 *
 */
const getCookie = (cname) => {
  const name = cname + "=";
  const decodedCookie = decodeURIComponent(document.cookie);

  const ca = decodedCookie.split(";");

  for (let i = 0; i < ca.length; i++) {
    let c = ca[i];

    while (c.charAt(0) == " ") {
      c = c.substring(1);
    }

    if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
  }

  return undefined;
};

/** Delete an entry from document.cookies
 *
 * @param {String} name
 */
const eraseCookie = (name) => createCookie(name, "", -1);

/** Makes a http fetch request (Without using the access-token)
 * NB This function takes a SINGLE parameter object
 *
 * @param {Object} param
 * @param {String} param.url  - default= get
 * @param {String} [param.method = GET]  - default= get
 * @param {Object} param.body
 *
 * @return {Promise}
 */
function httpRequest({ url, method = "GET", body, uri } = {}) {
  const href = uri ? uri : `${baseUrl}/${url}`;

  return new Promise((resolve) => {
    fetch(href, {
      method,
      body: JSON.stringify(body),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((response) => resolve(response))
      .catch((error) => console.error(error));
  });
}

/** Makes a http fetch request (USES the ACCESS-TOKEN obtained from signIn())
 * NB This function takes a SINGLE parameter object
 *
 * @param {Object} param
 * @param {String} param.url  - default= get
 * @param {String} [param.method = GET]  - default= get
 * @param {String} [param.uri] - the full http href
 * @param {Object} param.body
 *
 * @return {Promise}
 *
 */
function secureHttpRequest({ url, method = "GET", body, uri } = {}) {
  const accessToken = getCookie("access-token");

  if (!accessToken) {
    Promise.reject(
      Error("Your session has expired. you need to sign in again! !!")
    );
    alert("Your session has expired. you need to sign in again! !!");
    this.$router.history.push("/sign-in");
  }

  if (body) {
    body.user = getCookie("id");
  }

  return new Promise((resolve, reject) => {
    fetch(uri || `${baseUrl}/secure/${url}`, {
      method,
      body: JSON.stringify(body),
      headers: {
        "Content-Type": "application/json",
        "access-token": accessToken,
      },
    })
      .then((response) => {
        const accessToken = response.headers.get("access-token");
        const expirationDate = response.headers.get("expirationDate");

        document.cookie = `access-token=${accessToken}; expires=${expirationDate}; path=/`;

        return response.json();
      })
      .then((response) => {
        if (
          response.name === "InvalidAccessTokenError" ||
          response.name === "MissingAccessTokenError"
        ) {
          alert(response.message);
          this.$router.history.push("/sign-in");
        }
        resolve(response);
      })
      .catch((error) => reject(error));
  });
}

/** Patterns for html inputs*/
const patterns = {
  name: /^[A-Za-z][a-zA-Z0-9][^#&<>"~;^%{}!"]{1,}$/,
  email: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,})+$/,
  textarea: /^\w.[^#^%/=*'´,<>!"]+$/,
  // phone: /^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\s./0-9]*$/g,
  phone: /^[+0][0-9-]{5,}$/,
  password: /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/,
  date: /^(0?[1-9]|[12][0-9]|3[01])[/-](0?[1-9]|1[012])[/-]\d{4}$/,
};

/** This function takes a path and makes a delete request to back end to delete this file,
 *
 * @param {String} path relative path to the file to be deleted
 */
function deleteFile(path) {
  return new Promise((resolve, reject) => {
    ajax("/files", "DELETE", { path })
      .then(resolve)
      .catch("file deletion error: ", path);
  });
}

/** Read and parse a file */

function readFileContent(fileName) {
  fetch("resources/data.json")
    .then((response) => response.json())
    .catch((error) => alert(error));
}

/** Removes all children nested within a node
 * eg remove all the li in a ul
 * @param {Element} element the parent element
 */
function removeAllChildren(element) {
  while (element.firstChild) {
    element.removeChild(element.firstChild);
  }
}
