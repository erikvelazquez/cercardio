(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicineDetailController', MedicineDetailController);

    MedicineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medicine'];

    function MedicineDetailController($scope, $rootScope, $stateParams, previousState, entity, Medicine) {
        var vm = this;

        vm.medicine = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:medicineUpdate', function(event, result) {
            vm.medicine = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
