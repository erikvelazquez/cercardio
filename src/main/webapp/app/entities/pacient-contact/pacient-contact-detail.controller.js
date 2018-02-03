(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientContactDetailController', PacientContactDetailController);

    PacientContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PacientContact', 'Pacient'];

    function PacientContactDetailController($scope, $rootScope, $stateParams, previousState, entity, PacientContact, Pacient) {
        var vm = this;

        vm.pacientContact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:pacientContactUpdate', function(event, result) {
            vm.pacientContact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
