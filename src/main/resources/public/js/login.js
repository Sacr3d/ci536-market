document.getElementById("submitBtn").addEventListener("click", submit);

function submit() {
  var u = document.getElementById("userName").value;
  var p = document.getElementById("password").value;

  if (u != null && p != null) {
    var data = {
      username: u,
      password: p
    };
    postData("/auth/signin", data).then(data => {
      console.log(data); // JSON data parsed by `data.json()` call

      var user = data.username;
      var token = data.token;

      document.cookie = "token=" + token;
      document.cookie = "username=" + user;

      window.location.replace("/");
    });
  }
}
