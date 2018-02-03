(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PaymentDetailController', PaymentDetailController);

    PaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Payment'];

    function PaymentDetailController($scope, $rootScope, $stateParams, previousState, entity, Payment) {
        var vm = this;

        vm.payment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:paymentUpdate', function(event, result) {
            vm.payment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
