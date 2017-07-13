// used for load temp image, use always a function !!
function handleImageErrorLoad(image) {
   $(image).attr("src", $(image).attr("data_error_src"));
}

function showMessage (type, title, text) {
   new PNotify({
      title : title,
      text : text,
      type : type,
      animate : {
         animate : true,
         in_class : 'slideInDown',
         out_class : 'slideOutUp'
      },
      hide : true,
      styling : 'bootstrap3'
   });
}

// this function it is used to change the value of the checkbox that use switchery plugin programmatically
function fireCheckboxChanging(element) {
   if (typeof Event === 'function' || !document.fireEvent) {
       var event = document.createEvent('HTMLEvents');
       event.initEvent('change', true, true);
       element.dispatchEvent(event);
   } else {
      element.fireEvent('onchange');
   }
}

$(document).ready(function() {
   /* a message with PNotify is showed for 5 seconds */
   PNotify.prototype.options.delay ? (function() {
      PNotify.prototype.options.delay -= 500;
   }()) : false;

   /* fix select2 clear elements on form reset */
   $(".select2_multiple").select2({
   // maximumSelectionLength: 4,
   // placeholder: "With Max Selection limit 4",
   // allowClear: true
   }).on("change", function() {
      $(this).parsley().validate();
   }).closest("form").on("reset", function(ev) {
      var targetJQForm = $(ev.target);
      setTimeout((function() {
         $(this).find("select").trigger("change");
      }).bind(targetJQForm), 0);
   });
   
   
   /*clear all input on modal hide*/
   $('.modal').on('hidden.bs.modal', function () {
      $(this).find("input,textarea").val('').end();
   });
  
});
