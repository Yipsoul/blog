(function(){
    $(document).on('click',function(e){
        const DURATION = 1400    //动画时间
        const RISING_DISTANCE = 25 //   上升距离
        e = e || window.event
        let x = e.pageX
        let y = e.pageY
        let activeC = $('<div style="user-select:none"> ' + '♥' + '</div>').css({
            position:"absolute",
            zIndex : 999,
            fontSize : '2.0rem',
            color : getColor()              
        }).bind('selectStart',function(e){return false})

        $('body').append(activeC)

        activeC.css({
            left : x - (activeC.width()) / 2 + 'px',
            top : y - (activeC.height()) / 2 + 'px'
        }).animate({
            top : activeC.offset().top - RISING_DISTANCE + 'px',
            // left : activeC.offset().left + RISING_DISTANCE + 'px',
            opacity : 0
        },DURATION,function(){
            $(this).remove()
            
        })
    })
    
    //获取颜色值
    function getColor(){
        let color = '#'
        for(let i = 0; i < 3; i++){
            let r = Math.floor(Math.random() * 155+100)
            color += r.toString(16)
        }
        return color
    }
})()