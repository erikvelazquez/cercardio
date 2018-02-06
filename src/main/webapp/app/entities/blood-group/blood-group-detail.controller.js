(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('BloodGroupDetailController', BloodGroupDetailController);

    BloodGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BloodGroup'];

    function BloodGroupDetailController($scope, $rootScope, $stateParams, previousState, entity, BloodGroup) {
        var vm = this;

        vm.bloodGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:bloodGroupUpdate', function(event, result) {
            vm.bloodGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
