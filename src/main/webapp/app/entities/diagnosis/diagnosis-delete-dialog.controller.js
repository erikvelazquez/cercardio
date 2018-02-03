(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DiagnosisDeleteController',DiagnosisDeleteController);

    DiagnosisDeleteController.$inject = ['$uibModalInstance', 'entity', 'Diagnosis'];

    function DiagnosisDeleteController($uibModalInstance, entity, Diagnosis) {
        var vm = this;

        vm.diagnosis = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Diagnosis.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
