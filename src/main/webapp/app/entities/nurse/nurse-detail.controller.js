(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('NurseDetailController', NurseDetailController);

    NurseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Nurse', 'UserBD', 'Company', 'Gender'];

    function NurseDetailController($scope, $rootScope, $stateParams, previousState, entity, Nurse, UserBD, Company, Gender) {
        var vm = this;

        vm.nurse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:nurseUpdate', function(event, result) {
            vm.nurse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
