(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicNurseDetailController', MedicNurseDetailController);

    MedicNurseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MedicNurse', 'Medic', 'Nurse'];

    function MedicNurseDetailController($scope, $rootScope, $stateParams, previousState, entity, MedicNurse, Medic, Nurse) {
        var vm = this;

        vm.medicNurse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:medicNurseUpdate', function(event, result) {
            vm.medicNurse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
