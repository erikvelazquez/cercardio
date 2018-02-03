(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Medical_ProceduresDeleteController',Medical_ProceduresDeleteController);

    Medical_ProceduresDeleteController.$inject = ['$uibModalInstance', 'entity', 'Medical_Procedures'];

    function Medical_ProceduresDeleteController($uibModalInstance, entity, Medical_Procedures) {
        var vm = this;

        vm.medical_Procedures = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Medical_Procedures.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
