(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicalAnalysisDeleteController',MedicalAnalysisDeleteController);

    MedicalAnalysisDeleteController.$inject = ['$uibModalInstance', 'entity', 'MedicalAnalysis'];

    function MedicalAnalysisDeleteController($uibModalInstance, entity, MedicalAnalysis) {
        var vm = this;

        vm.medicalAnalysis = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MedicalAnalysis.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
