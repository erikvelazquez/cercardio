(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('AcademicDegreeDialogController', AcademicDegreeDialogController);

    AcademicDegreeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AcademicDegree'];

    function AcademicDegreeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AcademicDegree) {
        var vm = this;

        vm.academicDegree = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.academicDegree.id !== null) {
                AcademicDegree.update(vm.academicDegree, onSaveSuccess, onSaveError);
            } else {
                AcademicDegree.save(vm.academicDegree, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:academicDegreeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdat = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
