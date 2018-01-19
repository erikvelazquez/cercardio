(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('SocialHealthInsuranceDetailController', SocialHealthInsuranceDetailController);

    SocialHealthInsuranceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SocialHealthInsurance'];

    function SocialHealthInsuranceDetailController($scope, $rootScope, $stateParams, previousState, entity, SocialHealthInsurance) {
        var vm = this;

        vm.socialHealthInsurance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:socialHealthInsuranceUpdate', function(event, result) {
            vm.socialHealthInsurance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
