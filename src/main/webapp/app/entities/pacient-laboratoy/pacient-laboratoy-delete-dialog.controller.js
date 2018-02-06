(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientLaboratoyDeleteController',PacientLaboratoyDeleteController);

    PacientLaboratoyDeleteController.$inject = ['$uibModalInstance', 'entity', 'PacientLaboratoy'];

    function PacientLaboratoyDeleteController($uibModalInstance, entity, PacientLaboratoy) {
        var vm = this;

        vm.pacientLaboratoy = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PacientLaboratoy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
