(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientMedicalDataDeleteController',PacientMedicalDataDeleteController);

    PacientMedicalDataDeleteController.$inject = ['$uibModalInstance', 'entity', 'PacientMedicalData'];

    function PacientMedicalDataDeleteController($uibModalInstance, entity, PacientMedicalData) {
        var vm = this;

        vm.pacientMedicalData = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PacientMedicalData.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
