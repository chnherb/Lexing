$('button').on('click', function (e) {
    e.preventDefault();
    var id  = document.getElementById("bike").value; 
    // alert(id);
    var change_state = "bike_command=0&bike_id="+id;
    // alert(change_state);
    $.get('change_bike_state.php', change_state, function (data){
        // $('#test').html(data);
    });
    // $('ul').hide();
});