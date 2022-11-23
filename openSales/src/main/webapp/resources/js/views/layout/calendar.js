/**
 * 2022.05.17(TUE)
 * Author : tykim
 */


    

/*var Calendar = Backbone.Collection.extend({
    url: '/calendar'
})
*/

define(function(require){
var CalendarHome = Backone.View.extend({
    
        initialize : function(){
            
        },
        
        render : function (){
            
            var self = this;
            var calendar = new Calendar();
            calendar.fetch({
                success: function(){
                    console.log('Welcome Calendar');
                }
            })
        }
    })  
    
    return CalendarHome;
});
/*var Router = Backbone.Router.extend({
    routes : {
        '':'home'
    }
});

var calendarHome = new CalendarHome();

var router = new Router();
router.on('route:home',function(){
    calendarHome.render();
});*/