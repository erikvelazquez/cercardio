(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientMedicalAnalysisDeleteController',PacientMedicalAnalysisDeleteController);

    PacientMedicalAnalysisDeleteController.$inject = ['$uibModalInstance', 'entity', 'PacientMedicalAnalysis'];

    function PacientMedicalAnalysisDeleteController($uibModalInstance, entity, PacientMedicalAnalysis) {
        var vm = this;

        vm.pacientMedicalAnalysis = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PacientMedicalAnalysis.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
