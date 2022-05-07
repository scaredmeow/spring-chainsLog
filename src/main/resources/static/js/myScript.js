/**
 * 
 */
 
function signin() {
	let username = document.getElementById("username");
	let password = document.getElementById("password");
	if (username.value =="" || password.value == ""){
		alert("Blanks are not allowed");
		return false;
	} else {
		return true;
	}
}