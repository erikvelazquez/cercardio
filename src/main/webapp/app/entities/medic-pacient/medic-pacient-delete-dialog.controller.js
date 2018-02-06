(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicPacientDeleteController',MedicPacientDeleteController);

    MedicPacientDeleteController.$inject = ['$uibModalInstance', 'entity', 'MedicPacient'];

    function MedicPacientDeleteController($uibModalInstance, entity, MedicPacient) {
        var vm = this;

        vm.medicPacient = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MedicPacient.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
