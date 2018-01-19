(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('AcademicDegreeDeleteController',AcademicDegreeDeleteController);

    AcademicDegreeDeleteController.$inject = ['$uibModalInstance', 'entity', 'AcademicDegree'];

    function AcademicDegreeDeleteController($uibModalInstance, entity, AcademicDegree) {
        var vm = this;

        vm.academicDegree = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AcademicDegree.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
