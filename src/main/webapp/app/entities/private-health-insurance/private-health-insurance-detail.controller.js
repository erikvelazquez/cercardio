(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PrivateHealthInsuranceDetailController', PrivateHealthInsuranceDetailController);

    PrivateHealthInsuranceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PrivateHealthInsurance'];

    function PrivateHealthInsuranceDetailController($scope, $rootScope, $stateParams, previousState, entity, PrivateHealthInsurance) {
        var vm = this;

        vm.privateHealthInsurance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:privateHealthInsuranceUpdate', function(event, result) {
            vm.privateHealthInsurance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
