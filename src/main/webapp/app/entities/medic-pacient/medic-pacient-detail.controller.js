(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicPacientDetailController', MedicPacientDetailController);

    MedicPacientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MedicPacient', 'Medic', 'Pacient'];

    function MedicPacientDetailController($scope, $rootScope, $stateParams, previousState, entity, MedicPacient, Medic, Pacient) {
        var vm = this;

        vm.medicPacient = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:medicPacientUpdate', function(event, result) {
            vm.medicPacient = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
